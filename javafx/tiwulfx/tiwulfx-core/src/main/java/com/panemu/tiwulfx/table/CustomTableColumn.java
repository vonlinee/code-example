package com.panemu.tiwulfx.table;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.common.Validator;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a parent class for columns that display value from a POJO object.
 * There are two variations of concrete column implementation in regards with
 * cell editor:
 * <ul>
 * <li>Cell editor is a single control: TextField, CheckBox</li>
 * <li>Cell editor is a composite of controls: ComboBox, DateField, LookupField.
 * They are composed from a TextField and a button
 * </ul>
 * <p>
 * Please refer to {@link TextColumn} and {@link ComboBoxColumn} source code to
 * get reference on building your own column.
 * <p>
 * Column that doesn't extend CustomTableColumn, i.e: {@link TickColumn} will be
 * skipped by export-to-excel and paste routine
 * @param <R> Record data type
 * @param <C> Column data type
 * @author amrullah
 */
public class CustomTableColumn<R, C> extends TableColumn<R, C> {

    /**
     * used to get/set object method using introspection
     */
    private String propertyName;
    private final SimpleObjectProperty<TableCriteria<C>> tableCriteria = new SimpleObjectProperty<>();
    private C searchValue;
    private final Node filterImage = TiwulFXUtil.getGraphicFactory().createFilterGraphic();
    private Pos alignment = Pos.BASELINE_LEFT;
    private final ObservableMap<R, String> mapInvalid = FXCollections.observableHashMap();
    private final List<Validator<C>> lstValidator = new ArrayList<>();
    private String nullLabel = TiwulFXUtil.DEFAULT_NULL_LABEL;
    private final Map<R, RecordChange<R, C>> mapChangedRecord = new HashMap<>();

    private StringConverter<C> stringConverter = new StringConverter<C>() {

        @Override
        public String toString(C t) {
            if (t == null) {
                return nullLabel;
            } else {
                return t.toString();
            }
        }

        @Override
        public C fromString(String string) {
            throw new UnsupportedOperationException(CustomTableColumn.class.getName() + ". The implementation class of BaseColum should provide string converter by calling setStringConverter() in constructor.");
        }
    };


    TableCriteria<C> createSearchCriteria(TableCriteria.Operator operator, C value) {
        return new TableCriteria<>(propertyName, operator, value);
    }

    /**
     * @param propertyName java bean property name to be used for get/set method
     *                     using introspection
     * @param prefWidth    preferred column width
     */
    public CustomTableColumn(String propertyName, double prefWidth) {
        this(propertyName, prefWidth, TiwulFXUtil.getString(propertyName));
    }

    /**
     * @param propertyName java bean property name to be used for get/set method
     *                     using introspection
     * @param prefWidth    preferred column width
     * @param columnHeader column header text. Default equals propertyName. This
     *                     text is localized
     */
    public CustomTableColumn(String propertyName, double prefWidth, String columnHeader) {
        super(columnHeader);
        setPrefWidth(prefWidth);
        this.propertyName = propertyName;
//        setCellValueFactory(new PropertyValueFactory<S, T>(propertyName));
        tableCriteria.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (tableCriteria.get() != null) {
                    CustomTableColumn.this.setGraphic(filterImage);
                } else {
                    CustomTableColumn.this.setGraphic(null);
                }
            }
        });
        setCellValueFactory(new Callback<CellDataFeatures<R, C>, ObservableValue<C>>() {
            private SimpleObjectProperty<C> propertyValue;

            @Override
            @SuppressWarnings("unchecked")
            public ObservableValue<C> call(CellDataFeatures<R, C> param) {
                // This code is adapted from {@link javafx.scene.control.cell.PropertyValueFactory#getCellDataReflectively(java.lang.Object)}
                try {
                    Object cellValue;
                    if (getPropertyName().contains(".")) {
                        cellValue = PropertyUtils.getNestedProperty(param.getValue(), getPropertyName());
                    } else {
                        cellValue = PropertyUtils.getSimpleProperty(param.getValue(), getPropertyName());
                    }
                    propertyValue = new SimpleObjectProperty<>((C) cellValue);
                    return propertyValue;
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    /*
                       Ideally it catches org.apache.commons.beanutils.NestedNullException. However,
                       we need to import attaches bean utils library in FXML file to be able to display it in Scene Builder.
                       So, I decided to catch Exception to avoid the import.
                     */
                    return new SimpleObjectProperty<>(null);
                }
            }
        });

    }

    public StringConverter<C> getStringConverter() {
        return this.stringConverter;
    }

    public String getCellDataAsString(R rowItem) {
        StringConverter<C> converter = getStringConverter();
        if (converter == null) {
            return "";
        }
        return converter.toString(getCellData(rowItem));
    }

    public void setStringConverter(StringConverter<C> stringConverter) {
        this.stringConverter = stringConverter;
    }

    /**
     * Property that holds applied criteria to column
     * @return tableCriteria
     */
    public SimpleObjectProperty<TableCriteria<C>> tableCriteriaProperty() {
        return tableCriteria;
    }

    public String getNullLabel() {
        return nullLabel;
    }

    public void setNullLabel(String nullLabel) {
        this.nullLabel = nullLabel;
    }

    /**
     * Get criteria applied to this column
     * @return tableCriteria
     * @see #tableCriteriaProperty()
     */
    public TableCriteria<C> getTableCriteria() {
        return tableCriteria.get();
    }

    /**
     * Set criteria to be applied to column. If you are going to set criteria to
     * multiple columns, it is encouraged to call {@link TableControl#setReloadOnCriteriaChange(boolean)
     * }
     * and pass FALSE as parameter. It will disable autoload on criteria
     * change. After assign all criteria, call
     * {@link TableControl#reloadFirstPage()}. You might want to set {@link TableControl#setReloadOnCriteriaChange(boolean)
     * } back to true after that.
     * @param criteria
     * @see TableControl#setReloadOnCriteriaChange(boolean)
     */
    public void setTableCriteria(TableCriteria<C> criteria) {
        tableCriteria.set(criteria);
    }

    /**
     * Gets propertyName passed in constructor
     * @return 属性名称
     */
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Get Search Menu Item that is displayed in Table context menu
     * @return MenuItem
     */
    MenuItem getSearchMenuItem() {
        return null;
    }

    void setDefaultSearchValue(C searchValue) {
        this.searchValue = searchValue;
    }

    C getDefaultSearchValue() {
        return searchValue;
    }

    /**
     * Gets cell alignment
     * @return
     */
    public Pos getAlignment() {
        return alignment;
    }

    /**
     * Sets cell alignment
     * @param alignment alignment
     */
    public void setAlignment(Pos alignment) {
        this.alignment = alignment;
    }

    private final BooleanProperty filterable = new SimpleBooleanProperty(true);

    public BooleanProperty filterableProperty() {
        return filterable;
    }

    /**
     * Specifies whether right-clicking the column will show menu item to do
     * filtering. If filterable is true, search menu item will be displayed in
     * context menu.
     */
    public void setFilterable(boolean filterable) {
        this.filterable.set(filterable);
    }

    public boolean isFilterable() {
        return this.filterable.get();
    }

    private final BooleanProperty required = new SimpleBooleanProperty(false);

    /**
     * Set the field to required and cannot null. Some columns implementation
     * provide empty value that user can select if the column is not required.
     * @param required required
     */
    public void setRequired(boolean required) {
        this.required.set(required);
    }

    public boolean isRequired() {
        return required.get();
    }

    public BooleanProperty requiredProperty() {
        return required;
    }

    /**
     * Convert <code>stringValue</code> to value that is acceptable by this column.
     * @param stringValue stringValue
     * @return 列数据类型
     */
    public final C convertFromString(String stringValue) {
        return stringConverter.fromString(stringValue);
    }

    /**
     * Convert
     * <code>value</code> to String as represented in TableControl
     * @param value value
     * @return String
     */
    public final String convertToString(C value) {
        return stringConverter.toString(value);
    }

    /**
     * Set the value displayed in this column for specified record to valid. To
     * set it to invalid call {@link #setInvalid}
     */
    public void setValid(R record) {
        mapInvalid.remove(record);
    }

    /**
     * Set the value displayed in this column for specified record to invalid.
     * @param invalidMessage
     * @see #setValid(java.lang.Object)
     */
    public void setInvalid(R record, String invalidMessage) {
        mapInvalid.put(record, invalidMessage);
    }

    /**
     * Check whether specified record's value that displayed in this column is
     * valid. This checks against {@link #getInvalidRecordMap()}. If the record
     * is contained in the map that it is invalid.
     * @param record
     * @return true for valid
     */
    public boolean isValid(R record) {
        return !mapInvalid.containsKey(record);
    }

    /**
     * Get a Record-InvalidErrorMessage map that is managed by calls to {@link #setValid(java.lang.Object)}
     * and {@link #setInvalid(java.lang.Object, java.lang.String)}
     * @return ObservableMap
     */
    public ObservableMap<R, String> getInvalidRecordMap() {
        return mapInvalid;
    }

    /**
     * Add validator. The validator will be called with the same sequence the
     * validators are added to input controls. One validator instance is
     * reusable across columns, but only can be added once in a column.
     * @param validator
     */
    public void addValidator(Validator<C> validator) {
        if (!lstValidator.contains(validator)) {
            lstValidator.add(validator);
        }
    }

    public void removeValidator(Validator<C> validator) {
        lstValidator.remove(validator);
    }

    /**
     * Validate value contained in the input control. To make the input control
     * mandatory, call {@link #setRequired(boolean)} to true
     * @return false if invalid. True otherwise
     * @see #addValidator(com.panemu.tiwulfx.common.Validator) to add validator
     */
    public boolean validate(R record) {
        C value = getCellData(record);
        if (required.get() && (value == null || (value instanceof String && value.toString().trim().length() == 0))) {
            String msg = TiwulFXUtil.getString("field.mandatory");
            setInvalid(record, msg);
            return false;
        }
        //do not trim
        if (value instanceof String && ((String) value).length() == 0) {
            value = null;
        }
        if (value != null) {
            for (Validator<C> validator : lstValidator) {
                String msg = validator.validate(value);
                if (msg != null && !"".equals(msg.trim())) {
                    setInvalid(record, msg);
                    return false;
                }
            }
        }
        setValid(record);
        if (popup != null && popup.isShowing()) {
            popup.hide();
        }
        return true;
    }

    private PopupControl popup;
    Label errorLabel = new Label();

    PopupControl getPopup(R record) {
        String msg = mapInvalid.get(record);
        if (popup == null) {
            popup = new PopupControl();
            final HBox pnl = new HBox();
            pnl.getChildren().add(errorLabel);
            pnl.getStyleClass().add("error-popup");
            popup.setHideOnEscape(true);
        }
        errorLabel.setText(msg);
        return popup;
    }

    private final List<EditCommitListener<R, C>> lstEditCommitListener = new ArrayList<EditCommitListener<R, C>>();

    /**
     * Register a listener to editCommit event. The {@link TableColumn#setOnEditCommit(javafx.event.EventHandler) TableColumn.setOnEditCommit()}
     * doesn't work properly with TiwulFX's TableControl because there is no way to get
     * the old value.
     * @param listener EditCommitListener
     */
    public void addEditCommitListener(EditCommitListener<R, C> listener) {
        if (!lstEditCommitListener.contains(listener)) {
            lstEditCommitListener.add(listener);
        }
    }

    /**
     * Remove a listener of editCommit event.
     * @param listener EditCommitListener
     */
    public void removeEditCommitListener(EditCommitListener<R, C> listener) {
        lstEditCommitListener.remove(listener);
    }

    private void fireEditCommitChangeEvent(R record, C oldValue, C newValue) {
        for (EditCommitListener<R, C> listener : lstEditCommitListener) {
            listener.editCommited(this, record, oldValue, newValue);
        }
    }

    void addRecordChange(R record, C oldValue, C newValue) {
        if (mapChangedRecord.containsKey(record)) {
            RecordChange<R, C> rc = mapChangedRecord.get(record);
            rc.setNewValue(newValue);
        } else {
            RecordChange<R, C> rc = new RecordChange<>(record, getPropertyName(), oldValue, newValue);
            mapChangedRecord.put(record, rc);
        }

        /**
         * At this point, the value of the record is still the oldValue. The newValue
         * is assigned to the record after this method call ends (See TableControl onEditCommit).
         * In order the event is fired after the newValue is assigned, run the event in Platform.runLater
         */
        Platform.runLater(() -> fireEditCommitChangeEvent(record, oldValue, newValue));
    }

//    public void revertRecordChange(R record) {
//        RecordChange rc = mapChangedRecord.get(record);
//        if (rc != null) {
//            try {
//                PropertyUtils.setSimpleProperty(record, getPropertyName(), rc.getOldValue());
//                mapChangedRecord.remove(record);
//            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }

    void clearRecordChange() {
        mapChangedRecord.clear();
    }

    Map<R, RecordChange<R, C>> getRecordChangeMap() {
        return mapChangedRecord;
    }

    private final List<CellEditorListener<R, C>> lstValueChangeListener = new ArrayList<>();

    protected List<CellEditorListener<R, C>> getCellEditorListeners() {
        return lstValueChangeListener;
    }

    /**
     * Listen to cell editor's value change before it is committed to table cell.
     * To listen to commit event see {@link #addEditCommitListener(com.panemu.tiwulfx.table.EditCommitListener) addEditCommitListener()}
     * @param selectedValueListener
     */
    public void addCellEditorListener(CellEditorListener<R, C> selectedValueListener) {
        if (!lstValueChangeListener.contains(selectedValueListener)) {
            lstValueChangeListener.add(selectedValueListener);
        }
    }

    public void removeCellEditorListener(CellEditorListener<R, C> selectedValueListener) {
        lstValueChangeListener.remove(selectedValueListener);
    }
}

/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah .
 */
package com.panemu.tiwulfx.table;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.util.logging.Logger;

/**
 * @author Amrullah
 */
public abstract class BaseCell<R, C> extends TableCell<R, C> {

    private Logger logger = Logger.getLogger(BaseCell.class.getName());
    private Control control;
    private StringConverter<C> stringConverter;
    private static final PseudoClass PSEUDO_CLASS_INVALID = PseudoClass.getPseudoClass("invalid");
    private final CustomTableColumn<R, C> column;
    private boolean focusListenerAttached = false;

    public BaseCell(final CustomTableColumn<R, C> column) {
        this.stringConverter = column.getStringConverter();
        contentDisplayProperty().addListener(new ChangeListener<ContentDisplay>() {

            @Override
            public void changed(ObservableValue<? extends ContentDisplay> observable, ContentDisplay oldValue, ContentDisplay newValue) {
                if (control == null) {
                    control = getEditor();
                    attachEnterEscapeEventHandler();
                    setGraphic(control);
                }
                attachFocusListener();

//				if (isFocused() && isEditing()) {
//					commitEdit(getValueFromEditor());
//				}
                if (newValue == ContentDisplay.GRAPHIC_ONLY) {
                    setValueToEditor(getItem());
                }
            }
        });

        addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_EXITED || event.getEventType() == MouseEvent.MOUSE_MOVED) {
                    TableColumn<R, C> clm = getTableColumn();
                    if (clm instanceof CustomTableColumn && !((CustomTableColumn) clm).isValid(getTableRow().getItem())) {
                        CustomTableColumn<R, C> baseColumn = (CustomTableColumn<R, C>) clm;
                        PopupControl popup = baseColumn.getPopup((R) getTableRow().getItem());
                        if (event.getEventType() == MouseEvent.MOUSE_MOVED
                                && !popup.isShowing()) {

                            Point2D p = BaseCell.this.localToScene(0.0, 0.0);
                            popup.show(BaseCell.this,
                                    p.getX() + getScene().getX() + getScene().getWindow().getX(),
                                    p.getY() + getScene().getY() + getScene().getWindow()
                                            .getY() + BaseCell.this.getHeight() - 1);
                        } else if (event.getEventType() == MouseEvent.MOUSE_EXITED && popup.isShowing()) {
                            popup.hide();
                        }
                    }
                } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    /**
                     * We don't need this on java 8u05 because eventhough selection model is not cell-selection, we can get selected column using TablePos. However since 8u25 we cannot
                     * get selected column from TablePos unless selection model is cell-selection. To solve that, here we keep the information what column is clicked. This way we can
                     * get correct column to filter.
                     */
                    CustomTableView<R> ctv = (CustomTableView<R>) getTableView();
                    ctv.setSelectedColumn(getTableColumn());
                }
            }
        });

        this.column = column;
        column.getInvalidRecordMap().addListener(new WeakInvalidationListener(invalidRecordListener));
        itemProperty().addListener(invalidRecordListener);
        setAlignment(column.getAlignment());
    }

    private final InvalidationListener invalidRecordListener = new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            pseudoClassStateChanged(PSEUDO_CLASS_INVALID, column.getInvalidRecordMap()
                    .containsKey(BaseCell.this.getTableRow().getItem()));
        }
    };

    /**
     * For the case of TypeAhead, Date and Lookup, the focusable control is the textfield, not the control itself. This method is to be overridden by TypeAheadTableCell,
     * DateTableCell and LookupTableCell
     * @return
     */
    protected Control getFocusableControl() {
        return control;
    }

    protected void attachSkinListener() {
        if (control != null) {
            control.skinProperty().addListener((ov, oldValue, newValue) -> {
                if (oldValue == null && newValue != null) {
                    attachFocusListener();
                }
            });
        }
    }

    private void attachFocusListener() {
        if (focusListenerAttached) {
            return;
        }
        /**
         * Set cell mode to edit if the editor control receives focus. This is intended to deal with mouse click. This way, commitEdit() will be called if the cell is no longer
         * focused
         */
        if (getFocusableControl() == null && isEditable()) {
            attachSkinListener();
        } else {
            getFocusableControl().focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!BaseCell.this.isSelected() && newValue) {
                        getTableView().getSelectionModel().select(getIndex(), getTableColumn());
                    }
                    if (!isEditing() && newValue) {
                        programmaticallyEdited = true;
                        getTableView().edit(getIndex(), getTableColumn());
                        programmaticallyEdited = false;
                    }
                }
            });
            focusListenerAttached = true;
        }
    }

    private boolean programmaticallyEdited = false;

    @Override
    public void startEdit() {
        /**
         * If a row is added, new cells are created. The old cells are not disposed automatically. They still respond to user event's. Fortunately, the "should-be-discarded" cells
         * have invisible row so we can recognize them and prevent them to interact with user's event.
         */
        if (!this.getTableRow().isVisible() || !getTableRow().isEditable()) {
            return;
        }
        super.startEdit();
        if (!programmaticallyEdited) {

            if (control == null) {
                control = getEditor();
                attachEnterEscapeEventHandler();
                attachFocusListener();
            }
            setGraphic(control);
            setValueToEditor(getItem());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            /**
             * put focus on the textfield so user can directly typed on it
             */
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    control.requestFocus();
                }
            };
            Platform.runLater(r);
        }
    }

    protected abstract void setValueToEditor(C value);

    protected abstract C getValueFromEditor();

    protected abstract Control getEditor();


    @Override
    public void cancelEdit() {

        if (!isFocused() && isEditing()) {
            /**
             * The only way to commit edit in tableCell was by pressing enter. If user select another cell by clicking it or pressing tab than cell's value is reverted back. We want
             * to change this behavior. Now if user move to another cell, it's value is committed. The only way to revert the value is by pressing escape. Check {@link Cell}
             */
            logger.fine("about to commitEdit in cancelEdit() method");
            commitEdit(getValueFromEditor());
            return;
        }

        setValueToEditor(getItem());
        super.cancelEdit();

    }

    @Override
    public void updateItem(C item, boolean empty) {
        boolean emptyRow = getTableView().getItems().size() < getIndex() + 1;
        /**
         * don't call super.updateItem() because it will trigger cancelEdit() if the cell is being edited. It causes calling commitEdit() ALWAYS call cancelEdit as well which is
         * undesired.
         *
         */
        if (!isEditing()) {
            super.updateItem(item, empty && emptyRow);
        }
        if (empty && isSelected()) {
            updateSelected(false);
        }
        if (empty && emptyRow) {
            setText(null);
            //do not nullify graphic here. Let the TableRow to control cell dislay
        } else if (!isEditing()) {
            setText(getString(item));
            if (getContentDisplay() == ContentDisplay.GRAPHIC_ONLY) {
                setValueToEditor(item);
            }
        }
    }

    protected final String getString(C value) {
        try {
            return stringConverter.toString(value);
        } catch (ClassCastException ex) {
            String propertyName = getTableColumn() instanceof CustomTableColumn ? ((CustomTableColumn) getTableColumn()).getPropertyName() : "unknown";
            if (getTableRow() != null && getTableRow().getItem() != null) {
                propertyName = getTableRow().getItem().getClass().getName() + "." + propertyName;
            }
            String msg = "Invalid column type for \"" + propertyName + "\"";
            throw new RuntimeException(msg, ex);
        }
    }

    protected void attachEnterEscapeEventHandler() {
        control.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER && !t.isShiftDown()) {
                    commitEdit(getValueFromEditor());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

}

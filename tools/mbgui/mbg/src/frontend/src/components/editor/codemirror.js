/**
 * 直接复制vue-codemirror的脚本
 * @see vue-codemirror
 */
!function (e, t) {
    if ('object' == typeof exports && 'object' == typeof module) {
        module.exports = t(require('codemirror'));
    } else {
        let define = module['define'];
        // amd
        if ('function' == typeof define && define.amd) {
            define(['codemirror'], t);
        } else {
            if ('object' == typeof exports) {
                exports.VueCodemirror = t(require('codemirror'));
            } else {
                console.log(module.exports);
                module.exports.e = e;
                e.VueCodemirror = t(e.codemirror);
            }
        }
    }
}(this, function (e) {
    return function (e) {
        function t(r) {
            if (n[r]) return n[r].exports;
            let o = n[r] = {
                i: r,
                l: !1,
                exports: {},
            };
            return e[r].call(o.exports, o, o.exports, t), o.l = !0, o.exports;
        }

        let n = {};
        return t.m = e, t.c = n, t.i = function (e) {
            return e;
        }, t.d = function (
            e, n, r) {
            t.o(e, n) || Object.defineProperty(e, n, {
                configurable: !1,
                enumerable: !0,
                get: r,
            });
        }, t.n = function (e) {
            let n = e && e.__esModule
                ? function () {
                    return e.default;
                }
                : function () {
                    return e;
                };
            return t.d(n, 'a', n), n;
        }, t.o = function (e, t) {
            return Object.prototype.hasOwnProperty.call(e, t);
        }, t.p = '/', t(t.s = 3);
    }([
        function (t, n) {
            t.exports = e;
        }, function (e, t, n) {
            'use strict';
            Object.defineProperty(t, '__esModule', {value: !0});
            let r = n(0),
                o = function (e) {
                    return e && e.__esModule ? e : {default: e};
                }(r),
                i = window.CodeMirror || o.default;
            'function' != typeof Object.assign &&
            Object.defineProperty(Object, 'assign', {
                value: function (e, t) {
                    if (null == e) throw new TypeError(
                        'Cannot convert undefined or null to object');
                    for (let n = Object(e), r = 1; r < arguments.length; r++) {
                        let o = arguments[r];
                        if (null !=
                            o) for (let i in o) Object.prototype.hasOwnProperty.call(o,
                            i) && (n[i] = o[i]);
                    }
                    return n;
                },
                writable: !0,
                configurable: !0,
            }), t.default = {
                name: 'codemirror',
                data: function () {
                    return {
                        content: '',
                        codemirror: null,
                        cminstance: null,
                    };
                },
                props: {
                    code: String,
                    value: String,
                    marker: Function,
                    unseenLines: Array,
                    name: {
                        type: String,
                        default: 'codemirror',
                    },
                    placeholder: {
                        type: String,
                        default: '',
                    },
                    merge: {
                        type: Boolean,
                        default: !1,
                    },
                    options: {
                        type: Object,
                        default: function () {
                            return {};
                        },
                    },
                    events: {
                        type: Array,
                        default: function () {
                            return [];
                        },
                    },
                    globalOptions: {
                        type: Object,
                        default: function () {
                            return {};
                        },
                    },
                    globalEvents: {
                        type: Array,
                        default: function () {
                            return [];
                        },
                    },
                },
                watch: {
                    options: {
                        deep: !0,
                        handler: function (e) {
                            for (let t in e) this.cminstance.setOption(t, e[t]);
                        },
                    },
                    merge: function () {
                        this.$nextTick(this.switchMerge);
                    },
                    code: function (e) {
                        this.handerCodeChange(e);
                    },
                    value: function (e) {
                        this.handerCodeChange(e);
                    },
                },
                methods: {
                    initialize: function () {
                        let e = this,
                            t = Object.assign({}, this.globalOptions, this.options);
                        this.merge
                            ? (this.codemirror = i.MergeView(this.$refs.mergeview,
                                t), this.cminstance = this.codemirror.edit)
                            : (this.codemirror = i.fromTextArea(this.$refs.textarea,
                                t), this.cminstance = this.codemirror, this.cminstance.setValue(
                                this.code || this.value || this.content)), this.cminstance.on(
                            'change', function (t) {
                                e.content = t.getValue(), e.$emit &&
                                e.$emit('input', e.content);
                            });
                        let n = {};
                        [
                            'scroll',
                            'changes',
                            'beforeChange',
                            'cursorActivity',
                            'keyHandled',
                            'inputRead',
                            'electricInput',
                            'beforeSelectionChange',
                            'viewportChange',
                            'swapDoc',
                            'gutterClick',
                            'gutterContextMenu',
                            'focus',
                            'blur',
                            'refresh',
                            'optionChange',
                            'scrollCursorIntoView',
                            'update',
                        ].concat(this.events).concat(this.globalEvents).filter(function (e) {
                            return !n[e] && (n[e] = !0);
                        }).forEach(function (t) {
                            e.cminstance.on(t, function () {
                                for (let n = arguments.length, r = Array(n), o = 0; o <
                                n; o++) r[o] = arguments[o];
                                e.$emit.apply(e, [t].concat(r));
                                let i = t.replace(/([A-Z])/g, '-$1').toLowerCase();
                                i !== t && e.$emit.apply(e, [i].concat(r));
                            });
                        });
                        this.$emit('ready',
                            this.codemirror), this.unseenLineMarkers(), this.refresh();
                    },
                    refresh: function () {
                        let e = this;
                        this.$nextTick(function () {
                            e.cminstance.refresh();
                        });
                    },
                    destroy: function () {
                        let e = this.cminstance.doc.cm.getWrapperElement();
                        e && e.remove && e.remove();
                    },
                    handerCodeChange: function (e) {
                        if (e !== this.cminstance.getValue()) {
                            let t = this.cminstance.getScrollInfo();
                            this.cminstance.setValue(
                                e), this.content = e, this.cminstance.scrollTo(t.left, t.top);
                        }
                        this.unseenLineMarkers();
                    },
                    unseenLineMarkers: function () {
                        let e = this;
                        void 0 !== this.unseenLines && void 0 !== this.marker &&
                        this.unseenLines.forEach(function (t) {
                            let n = e.cminstance.lineInfo(t);
                            e.cminstance.setGutterMarker(t, 'breakpoints',
                                n.gutterMarkers ? null : e.marker());
                        });
                    },
                    switchMerge: function () {
                        let e = this.cminstance.doc.history,
                            t = this.cminstance.doc.cleanGeneration;
                        this.options.value = this.cminstance.getValue(), this.destroy(), this.initialize(), this.cminstance.doc.history = e, this.cminstance.doc.cleanGeneration = t;
                    },
                },
                mounted: function () {
                    this.initialize();
                },
                beforeDestroy: function () {
                    this.destroy();
                },
            };
        }, function (e, t, n) {
            'use strict';
            Object.defineProperty(t, '__esModule', {value: !0});
            let r = n(1), o = n.n(r);
            for (let i in r) ['default', 'default'].indexOf(i) < 0 &&
            function (e) {
                n.d(t, e, function () {
                    return r[e];
                });
            }(i);
            let s = n(5), c = n(4), a = c(o.a, s.a, !1, null, null, null);
            t.default = a.exports;
        }, function (e, t, n) {
            'use strict';

            function r(e) {
                return e && e.__esModule ? e : {default: e};
            }

            Object.defineProperty(t, '__esModule',
                {value: !0}), t.install = t.codemirror = t.CodeMirror = void 0;
            let o = n(0), i = r(o), s = n(2), c = r(s),
                a = window.CodeMirror || i.default, u = function (e, t) {
                    t && (t.options &&
                    (c.default.props.globalOptions.default = function () {
                        return t.options;
                    }), t.events &&
                    (c.default.props.globalEvents.default = function () {
                        return t.events;
                    })), e.component(
                        c.default.name, c.default);
                }, l = {
                    CodeMirror: a,
                    codemirror: c.default,
                    install: u,
                };
            t.default = l, t.CodeMirror = a, t.codemirror = c.default, t.install = u;
        }, function (e, t) {
            e.exports = function (e, t, n, r, o, i) {
                let s, c = e = e || {}, a = typeof e.default;
                'object' !== a && 'function' !== a || (s = e, c = e.default);
                let u = 'function' == typeof c ? c.options : c;
                t &&
                (u.render = t.render, u.staticRenderFns = t.staticRenderFns, u._compiled = !0), n &&
                (u.functional = !0), o && (u._scopeId = o);
                let l;
                if (i ? (l = function (e) {
                    e = e || this.$vnode && this.$vnode.ssrContext || this.parent &&
                        this.parent.$vnode && this.parent.$vnode.ssrContext, e ||
                    'undefined' == typeof __VUE_SSR_CONTEXT__ ||
                    (e = __VUE_SSR_CONTEXT__), r && r.call(this, e), e &&
                    e._registeredComponents && e._registeredComponents.add(i);
                }, u._ssrRegister = l) : r && (l = r), l) {
                    let f = u.functional, d = f ? u.render : u.beforeCreate;
                    f ? (u._injectStyles = l, u.render = function (e, t) {
                        return l.call(t), d(e, t);
                    }) : u.beforeCreate = d ? [].concat(d, l) : [l];
                }
                return {
                    esModule: s,
                    exports: c,
                    options: u,
                };
            };
        }, function (e, t, n) {
            'use strict';
            let r = function () {
                let e = this, t = e.$createElement, n = e._self._c || t;
                return n('div', {
                    staticClass: 'vue-codemirror',
                    class: {merge: e.merge},
                }, [
                    e.merge ? n('div', {ref: 'mergeview'}) : n('textarea', {
                        ref: 'textarea',
                        attrs: {
                            name: e.name,
                            placeholder: e.placeholder,
                        },
                    }),
                ]);
            }, o = [], i = {
                render: r,
                staticRenderFns: o,
            };
            t.a = i;
        },
    ]);
});
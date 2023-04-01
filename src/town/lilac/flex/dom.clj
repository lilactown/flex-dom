(ns town.lilac.flex.dom
  (:require
   [town.lilac.flex :as flex]
   [town.lilac.dom :as dom])
  (:refer-clojure :exclude [map meta time]))

(defmacro scope
  [& body]
  `(let [pfn# (fn [] ~@body)]
     (flex/effect
      ([] (pfn#))
      ([el#]
       (dom/patch-outer el# pfn#)))))


(defn reactive-body?
  [body]
  (cond
    (seq? body) (when-not (= `$ (first body))
                  (some reactive-body? body))
    (coll? body) (some reactive-body? body)
    :else (= `deref body)))


(defmacro $
  [sym & body]
  (let [body `(~(symbol "town.lilac.dom" (name sym))
               ~@body)]
    (if (reactive-body? body)
      `(scope ~body)
      body)))

(comment
  (reactive-body?
   `(let [foo @bar]
      (dom/text "baz")))

  (reactive-body?
   `(let [foo bar]
      ($ div @foo)))

  (macroexpand '($ div ($ text "hi")))

  (macroexpand '($ text @foo)))


(declare
 input textarea option select a abbr address area article aside audio b base bdi
 bdo big blockquote body br button canvas caption cite code col colgroup data datalist
 dd del details dfn dialog div dl dt em embed fieldset figcaption figure footer form
 h1 h2 h3 h4 h5 h6 head header hr html i iframe img ins kbd keygen label legend li link
 main map mark menu menuitem meta meter nav noscript object ol optgroup output p param
 picture pre progress q rp rt ruby s samp script section small source span strong style
 sub summary sup table tbody td tfoot th thead time title tr track u ul var video wbr
 circle clipPath ellipse g line mask path pattern polyline rect svg defs
 linearGradient polygon radialGradient stop tspan text)

(def tags
  '[input textarea option select a abbr address area article aside audio
    b base bdi bdo big blockquote body br button canvas caption cite code col
    colgroup data datalist dd del details dfn dialog div dl dt em embed fieldset
    figcaption figure footer form h1 h2 h3 h4 h5 h6 head header hr html i iframe
    img ins kbd keygen label legend li link main map mark menu menuitem meta
    meter nav noscript object ol optgroup output p param picture pre progress q
    rp rt ruby s samp script section small source span strong style sub summary
    sup table tbody td tfoot th thead time title tr track u ul var video wbr
    text])

(defn gen-tag
  [tag]
  `(defmacro ~tag [& args#]
     `($ ~(str '~tag) ~@args#)))

(defmacro gen-tags
  []
  `(do
     ~@(for [tag tags]
         (gen-tag tag))))

(gen-tags)

(comment
  (macroexpand `(div @foo)))

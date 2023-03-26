(ns town.lilac.flex.dom
  (:require
   [town.lilac.flex :as flex]
   [town.lilac.dom :as dom]))

(defmacro scope
  [& body]
  `(let [pfn# (fn [] ~@body)]
     (flex/effect
      ([] (pfn#))
      ([el#]
       (dom/patch-outer el# pfn#)))))

(ns town.lilac.flex.dom
  (:require
   [town.lilac.flex :as flex]
   [town.lilac.dom :as dom]))

(defmacro track
  [& body]
  `(let [pfn# (fn [] ~@body)
         fx# (flex/effect
              ([] (pfn#))
              ([el#]
               (dom/patch-outer el# pfn#)))]
     (fx#)))

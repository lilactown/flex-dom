(ns town.lilac.flex.dom
  (:require
   [town.lilac.flex :as flex]
   [town.lilac.dom :as dom])
  #?(:cljs (:require-macros [town.lilac.flex.dom])))

(defmacro track
  [& body]
  `(let [pfn# (fn [] ~@body)
         fx# (flex/effect
              ([] (pfn#))
              ([el#]
               (dom/patch-outer el# pfn#)))]
     (fx#)))

#?(:cljs
   (defn ->root
     "Creates a DOM effect root, which when called like a function will start
  the app function `f` in the container element `el`.

  Subsequent calls will restart it, re-rendering the entire app."
     [el f]
     (let [fx (flex/effect
               []
               (dom/patch el f))
           root-var (atom nil)]
       (reify
         IFn
         (-invoke [_]
           ;; dispose
           (when-let [dispose @root-var]
             (dispose))
           ;; start
           (reset! root-var (fx)))))))

(comment
  (def counter (flex/source 0))

  (def root
    (->root
     (js/document.getElementById "app")
     (fn []
       (town.lilac.flex.dom/track
        (dom/div
         (dom/text @counter))))))

  (root)

  (counter inc))

(ns town.lilac.flex.dom
  (:require
   [town.lilac.flex :as flex]
   [town.lilac.dom :as dom])
  (:require-macros [town.lilac.flex.dom :refer [track]]))

(defrecord Root [el ^:mutable current fx]
  IFn
  (-invoke [_]
    (when current
      (current))
    ;; start
    (set! current (fx))))


(defn create-root
     "Creates a DOM effect root, which when called like a function will start
  the app function `f` in the container element `el`.

  Subsequent calls will restart it, re-rendering the entire app."
     [el f]
     (let [fx (flex/effect
               []
               (dom/patch el f))]
       (->Root el (fx) fx)))


(comment
  (def counter (flex/source 0))

  (def root
    (create-root
     (js/document.getElementById "app")
     (fn []
       (dom/div
        (dom/text @counter)))))

  (root)

  (counter inc))

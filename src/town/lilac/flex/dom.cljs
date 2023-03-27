(ns town.lilac.flex.dom
  (:require
   [town.lilac.flex :as flex]
   [town.lilac.dom :as dom])
  (:require-macros [town.lilac.flex.dom :refer [scope]]))

(defn create-root
  "Returns a root DOM effect which will render the app function `f` inside of
  DOM node `el`. `town.lilac.flex/dispose!` and `town.lilac.flex/run!` can be
  used to stop and start it."
  [el f]
  (flex/effect [] (dom/patch el f)))

(comment
  (def counter
    (flex/source 0))

  (defn app
    []
    (dom/div
     (scope (dom/text @counter))
     (dom/button
      {:onclick #(counter inc)}
      (dom/text "+"))))

  (def root
    (create-root (js/document.getElementById "app") app))

  ;; restart e.g. on hot reload
  (flex/dispose! root)
  (flex/run! root)

  (counter inc)

  (counter 0))

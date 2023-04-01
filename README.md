# flex-dom

Build front end web apps using [flex](https://github.com/lilactown/flex).

## Installation

Install via git deps

```clojure
town.lilac/flex-dom {:git/url "https://github.com/lilactown/flex-dom"
                     :git/sha "e880c78d1d755bc19f6f54fd0bfc4f6fdc6f9a0d"}
```

## Usage

```clojure
(ns my-app.main
  (:require
   [town.lilac.flex :as flex]
   [town.lilac.flex.dom :as dom]))

(def counter
  (flex/source 0))

(defn app
  []
  (dom/div
   ;; updates triggered by `counter` are automatically scoped to only this text node
   (dom/text @counter)
   (dom/button
    {:onclick #(counter inc)} ; increment the counter on click
    (dom/text "+"))))

(defonce root
  (flex.dom/create-root
   (js/document.getElementById "app")
   #(app)))

(defn ^:dev/after-load start!
  []
  ;; restart the root effect
  (flex/dispose! root)
  (flex/run! root))


(comment
  ;; change the state of the counter from the REPL
  (counter inc)
  (counter 0))
```

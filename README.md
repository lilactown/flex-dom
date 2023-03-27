# flex-dom

Build front end web apps using [flex](https://github.com/lilactown/flex).

## Installation

Install via git deps

```clojure
town.lilac/flex-dom {:git/url "https://github.com/lilactown/flex-dom"
                     :git/sha "c48316b1c07da18d2d3c459a606bb13350c88d00"}
```

## Usage

```clojure
(ns my-app.main
  (:require
   [town.lilac.dom :as dom]
   [town.lilac.flex :as flex]
   [town.lilac.flex.dom :as flex.dom]))

(def counter
  (flex/source 0))

(defn app
  []
  (dom/div
   ;; scope updates triggered by `counter` to only this text node
   (flex.dom/scope
    (dom/text @counter))
   (dom/button
    {:onclick #(counter inc)} ; increment the counter on click
    (dom/text "+"))))

(def root
  (flex.dom/create-root
   (js/document.getElementById "app")
   app))

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

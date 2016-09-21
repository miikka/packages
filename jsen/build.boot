(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "0.6.3")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/jsen
       :version     +version+
       :description "JSON-Schema validator built for speed"
       :url         "https://github.com/bugventure/jsen"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(deftask package []
  (comp
    (download :url (str "https://github.com/bugventure/jsen/archive/v" +lib-version+ ".zip")
              :unzip true)
    (sift :move {#"^jsen-[0-9.]+/dist/jsen.js"      "cljsjs/jsen/development/jsen.inc.js"
                 #"^jsen-[0-9.]+/dist/jsen.min.js"  "cljsjs/jsen/production/jsen.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.jsen")
    (pom)
    (jar)))

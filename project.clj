(defproject lobos "1.0.0-beta3"
  :description "A library to create and manipulate SQL database schemas."
  :url "http://budu.github.com/lobos/"
  :license {:name "Eclipse Public License"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.clojure/tools.macro "0.1.5"]]
  :profiles {:dev
             {:dependencies
              [[lein-clojars "0.9.1"]
               [lein-marginalia "0.9.0"]
               [lein-multi "1.1.0"]
               [cljss "0.1.1"]
               [hiccup "1.0.5"]
               [com.h2database/h2 "1.4.193"]]}}
  :jar-exclusions [#"www.clj" #"config.clj" #"migrations.clj"]
  :min-lein-version "2.0.0")

;; Copyright (c) Nicolas Buduroi. All rights reserved.
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0 which can be found in the file
;; epl-v10.html at the root of this distribution. By using this software
;; in any fashion, you are agreeing to be bound by the terms of this
;; license.
;; You must not remove this notice, or any other, from this software.

(ns lobos.test.connectivity
  (:require [clojure.test :refer :all]
            [lobos.connectivity :refer :all]))

(def ^{:dynamic true} *cnx*)

(defn cnx-fixture [f]
  (binding [*cnx* (reify java.sql.Connection (close [this] nil))
            *get-cnx* (fn [& _] *cnx*)]
    (f)))

(use-fixtures :each cnx-fixture)

(deftest test-open-and-close-global
  (open-global {})
  (is (= @global-connections
         {:default-connection {:connection *cnx* :db-spec {}}})
      "Opening a default global connection")
  (close-global)
  (is (= @global-connections {})
      "Closing a default global connection")
  (open-global :foo {})
  (is (= @global-connections
         {:foo {:connection *cnx* :db-spec {}}})
      "Opening a named global connection")
  (close-global :foo)
  (is (= @global-connections {})
      "Closing a named global connection")
  (open-global :foo {:unsafe true})
  (open-global :foo {})
  (is (= @global-connections
         {:foo {:connection *cnx* :db-spec {}}})
      "Re-opening a named global connection")
  (is (thrown? Exception
               (open-global :foo nil))
      "Opening a global connection with a existing name")
  (close-global :foo)
  (is (thrown? Exception
               (close-global :foo))
      "Closing an inexistant global connection"))

(deftest test-default-connection
  (open-global {})
  (is (= (default-connection)
         *cnx*)
      "Default connection")
  (close-global))

(deftest test-get-db-spec
  (is (= {} (get-db-spec {}))
      "Get db-spec from db-spec")
  (open-global {})
  (is (= {} (get-db-spec))
      "Get db-spec from global connection")
  (close-global))

package ru.kroshchenko.ruven.controllers.rest.resources;

public class ApplicationInfo {
        private String version;

        public ApplicationInfo() {
        }

        public ApplicationInfo(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
package main;

/**
 * A movie genre
 */
public enum MovieGenre {
    ACTION {
        @Override
        public String toString() {
            return "Action";
        }
    },
    ADVENTURE {
        @Override
        public String toString() {
            return "Adventure";
        }
    },
    COMEDY {
        @Override
        public String toString() {
            return "Comedy";
        }
    },
    CRIME_AND_MYSTERY {
        @Override
        public String toString() {
            return "Crime and Mystery";
        }
    },
    FANTASY {
        @Override
        public String toString() {
            return "Fantasy";
        }
    },
    HISTORICAL {
        @Override
        public String toString() {
            return "Historical";
        }
    },
    HORROR {
        @Override
        public String toString() {
            return "Horror";
        }
    },
    ROMANCE {
        @Override
        public String toString() {
            return "Romance";
        }
    },
    SATIRE {
        @Override
        public String toString() {
            return "Satire";
        }
    },
    SCIENCE_FICTION {
        @Override
        public String toString() {
            return "Science Fiction";
        }
    },
    SPECULATIVE {
        @Override
        public String toString() {
            return "Speculative";
        }
    },
    THRILLER {
        @Override
        public String toString() {
            return "Thriller";
        }
    },
    WESTERN {
        @Override
        public String toString() {
            return "Western";
        }
    },
    OTHER {
        @Override
        public String toString() {
            return "Other";
        }
    },
    
    DRAMA {
        @Override
        public String toString() {
            return "Drama";
        }
    },
    
    MYSTERY {
        @Override
        public String toString() {
            return "Mystery";
        }
    }


}

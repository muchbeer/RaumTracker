package muchbeer.raum.com.raumtracker.utilitiy;

public class RaumData {

    private int id;
        private String date_trucked;
                private String coordinate_track;
        private int dat_accuracy;
        private String street_name;


        public RaumData(int id, String mcoordinate_track, String mdate_trucked, int mdat_accuracy,
                        String mstreet_name) {
            this.id = id;
            this.coordinate_track = mcoordinate_track;
            this.date_trucked = mdate_trucked;
            this.dat_accuracy = mdat_accuracy;
            this.street_name = mstreet_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoordinate_track() {
            return coordinate_track;
        }

        public void setCoordinate_track(String coordinate_track) {
            this.coordinate_track = coordinate_track;
        }

        public String getDate_trucked() {
            return date_trucked;
        }

        public void setDate_trucked(String mdate_trucked) {
            this.date_trucked = mdate_trucked;
        }

        public int getDat_accuracy() {
            return dat_accuracy;
        }

        public void setDat_accuracy(int mdat_accuracy) {
            this.dat_accuracy = mdat_accuracy;
        }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String mstreet_name) {
        this.street_name = mstreet_name;
    }
}
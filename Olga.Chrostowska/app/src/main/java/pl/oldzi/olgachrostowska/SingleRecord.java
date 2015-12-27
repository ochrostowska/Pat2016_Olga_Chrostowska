package pl.oldzi.olgachrostowska;

public class SingleRecord {

    private String jsonTitle;
    private String jsonDesc;
    private String jsonUrl;

    public SingleRecord(String title, String desc, String url){
        this.jsonTitle = title;
        this.jsonDesc = desc;
        this.jsonUrl = url;
    }

    public String getJsonDesc() {
        return jsonDesc;
    }

    public String getJsonTitle() {
        return jsonTitle;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }
}

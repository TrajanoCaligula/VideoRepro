/**
 *
 * @author name
 */
public class Video {
    
    private int id;
    private String title;
    private String author;
    private Date creationDate;
    private Date uploadDate;
    private Time duration;
    private long views;
    private String description;
    private String mime;
    private URL videoUrl;
    private URL imageUrl;
    private String language;

    public Video(int id, String title, String author, Date creationDate, 
            Date uploadDate, Time duration, long views, String description, 
            String mime, URL videoUrl, URL imageUrl, String language) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.uploadDate = uploadDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.mime = mime;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.language = language;
    }
    
    public Video(String title, String author, Date creationDate, Time duration, 
                String description, String mime, URL videoUrl, URL imageUrl, 
                String language ) {
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.description = description;
        this.mime = mime;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.language = language;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUploadDate() {
        return this.uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Time getDuration() {        
        return this.duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public long getViews() {
        return this.views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMime() {
        return this.mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public URL getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(URL url) {
        this.videoUrl = url;
    }
    
    public URL getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(URL url) {
        this.imageUrl = url;
    }
    
    public String getLanguage() {
        return this.language.substring(0,1).toUpperCase() + this.language.substring(1);
    }
    
    public void setLanguage( String language ) {
        this.language = language;
    }
    
    
}
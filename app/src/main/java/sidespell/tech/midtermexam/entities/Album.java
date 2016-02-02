package sidespell.tech.midtermexam.entities;


/**
 * This is a representation of a movie.
 */
public class Album {

    private String mAlbum;
    private String mArtist;
    private int    mImageId;


    public Album() {
    }

    public Album(String album, String artist, int imageId) {
        mAlbum = album;
        mArtist = artist;
        mImageId = imageId;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public Album setAlbum(String album) {
        mAlbum = album;
        return this;
    }

    public String getArtist() {
        return mArtist;
    }

    public Album setArtist(String artist) {
        mArtist = artist;
        return this;
    }

    public int getImageId() {
        return mImageId;
    }

    public Album setImageId(int imageId) {
        mImageId = imageId;
        return this;
    }
}
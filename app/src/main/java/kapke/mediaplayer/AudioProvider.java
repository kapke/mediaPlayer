package kapke.mediaplayer;

import java.net.URI;
import java.util.List;

/**
 * Created by kapke on 23.10.14.
 */
public interface AudioProvider {
    public void setAudioExtensions (String[] exts);
    public List<URI> getFiles ();
}

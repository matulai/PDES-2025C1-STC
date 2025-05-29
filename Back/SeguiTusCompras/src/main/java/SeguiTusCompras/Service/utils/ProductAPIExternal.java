package SeguiTusCompras.Service.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductAPIExternal {
    public String id;
    public String name;
    public String domain_id;
    public Description short_description;
    public List<PictureDto> pictures;

    public static class PictureDto {
        public String url;
    }

    public static class Description {
        public String content;
    }
}

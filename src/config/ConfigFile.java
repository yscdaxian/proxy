package config;

import java.util.List;
import java.util.Map;

public abstract interface ConfigFile
{
  public abstract String getFilename();

  public abstract Map<String, List<String>> getCategories();

  public abstract String getValue(String paramString1, String paramString2);

  public abstract List<String> getValues(String paramString1, String paramString2);
}

/* Location:           F:\asterisk\腾星产品\腾星中间件\商业版\朄1�7后发的版本第三版\txctieev1_0.jar
 * Qualified Name:     teltion.config.ConfigFile
 * JD-Core Version:    0.6.0
 */
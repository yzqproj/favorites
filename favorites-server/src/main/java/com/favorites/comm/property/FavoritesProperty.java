package com.favorites.comm.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yanni
 * @date time 2022/1/20 20:08
 * @modified By:
 */
@Component
@ConfigurationProperties(prefix = "favorites")
public class FavoritesProperty {
}

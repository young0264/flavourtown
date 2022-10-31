package com.flavourtown.web.dto.favorite;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {
    int favoriteCount;
    boolean isFavorite;
}

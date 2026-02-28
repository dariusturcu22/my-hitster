package org.dariusturcu.backend.model.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SongMetadataResponse(
        String title,
        String artist,
        @JsonDeserialize(using = FlexibleYearDeserializer.class)
        Integer releaseYear,
        String gradientColor1,
        String gradientColor2
) {

}

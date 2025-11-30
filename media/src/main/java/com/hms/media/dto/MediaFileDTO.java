package com.hms.media.dto;


import com.hms.media.entity.Storage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaFileDTO {
    private Long id;
    private String name;
    private String type;
    private Long size;

}

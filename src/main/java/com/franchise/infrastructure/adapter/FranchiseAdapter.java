package com.franchise.infrastructure.adapter;

import com.franchise.domain.entity.Franchise;
import io.r2dbc.spi.Readable;
import java.util.Objects;

public class FranchiseAdapter {

  public static Franchise adapt(final Readable row) {
    return Franchise.builder()
        .id(Objects.requireNonNull(row.get(Franchise.PROPERTY_ID, Integer.class)))
        .name(row.get(Franchise.PROPERTY_NAME, String.class))
        .status(row.get(Franchise.PROPERTY_STATUS, String.class))
        .build();
  }
}

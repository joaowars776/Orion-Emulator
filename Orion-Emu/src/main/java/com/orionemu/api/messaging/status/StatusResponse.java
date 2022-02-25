package com.orionemu.api.messaging.status;

import com.orionemu.api.stats.OrionStats;
import io.coerce.services.messaging.client.messages.response.MessageResponse;

public class StatusResponse implements MessageResponse {
    private final OrionStats status;
    private final String version;

    public StatusResponse(final OrionStats status, final String version) {
        this.status = status;
        this.version = version;
    }

    public OrionStats getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }
}

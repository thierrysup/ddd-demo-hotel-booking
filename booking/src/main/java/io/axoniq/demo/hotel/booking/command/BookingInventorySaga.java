/*
 * Copyright (c) 2020-2020. AxonIQ
 *
 * Licensed under the Apache License, Version 2.0 (the &quot;License&quot;)
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.axoniq.demo.hotel.booking.command;

import io.axoniq.demo.hotel.booking.command.api.AddRoomCommand;
import io.axoniq.demo.hotel.booking.command.api.RoomAddedEvent;
import io.axoniq.demo.hotel.inventory.command.api.MarkRoomAsAddedToBookingSystemCommand;
import io.axoniq.demo.hotel.inventory.command.api.RoomAddedToInventoryEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Saga
@Slf4j
@ProcessingGroup("BookingInventorySaga")
public class BookingInventorySaga {

    @Autowired
    private transient CommandGateway commandGateway;
    private UUID inventoryRoomId;

    @StartSaga
    @SagaEventHandler(associationProperty = "roomNumber")
    public void on(RoomAddedToInventoryEvent event) {
        this.inventoryRoomId = event.getRoomId();
        log.info("onRoomAddedToInventory  id: {} &  event: {}", event, inventoryRoomId);
        commandGateway.send(new AddRoomCommand(event.getRoomNumber(), event.getRoomId(), event.getRoomDescription()));

    }

    @EndSaga
    @SagaEventHandler(associationProperty = "roomNumber")
    public void on(RoomAddedEvent event) {
        log.info("RoomAddedEvent from booking side  id: {} &  event: {}", this.inventoryRoomId, event);

        commandGateway.send(new MarkRoomAsAddedToBookingSystemCommand(event.getRoomId()), (commandMessage, commandResultMessage) -> {
            log.error("Error is result: {}", commandResultMessage.exceptionResult().getCause());
             });
    }
}

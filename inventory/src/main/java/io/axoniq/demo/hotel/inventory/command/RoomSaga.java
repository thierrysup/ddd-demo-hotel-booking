package io.axoniq.demo.hotel.inventory.command;

import io.axoniq.demo.hotel.inventory.command.api.MarkRoomAsAddedToBookingSystemCommand;
import io.axoniq.demo.hotel.inventory.command.api.RoomAddedToBookingSystemEvent;
import io.axoniq.demo.hotel.inventory.command.api.RoomAddedToInventoryEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

//TODO: This normaly we don't need this Saga... when we can use multi-context in DDD.
//We can do it more simplified with sending MarkRoomAsAddedToBookingSystemCommand from BookingInventorySaga in Booking Context.
@Component
//@Saga
@Slf4j
//@ProcessingGroup("RoomInventorySaga")
public class RoomSaga {

//    @Autowired
//    private transient CommandGateway commandGateway;
//    private UUID inventoryRoomId;
//
//    @StartSaga
//    @SagaEventHandler(associationProperty = "roomNumber")
//    public void on(RoomAddedEvent event) {
//        this.inventoryRoomId = event.getRoomId();
//        log.info("RoomAddedEvent from booking side  id: {} &  event: {}", inventoryRoomId, event);
//        var result = commandGateway.send(new MarkRoomAsAddedToBookingSystemCommand(event.getRoomId()));
//        log.info("ChangeRoomStatusCommand result {}", result);
//    }
//
//    @EndSaga
//    @SagaEventHandler(associationProperty = "roomId")
//    public void on(RoomAddedToBookingSystemEvent event) {
//        log.info("RoomAddedToBookingSystemEvent  id: {} &  event: {}", event.getRoomId(), event);
//    }
}

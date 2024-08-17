import React from 'react';
import { useDrag } from 'react-dnd';

const DraggableEvent = ({ event }) => {
  const [, drag] = useDrag({
    type: 'event',
    item: { id: event.id },
  });

  return (
    <div ref={drag}>
      {event.title}
    </div>
  );
};

export default DraggableEvent;

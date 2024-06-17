import React from 'react';
import { useDrag } from 'react-dnd';

const DraggableEvent = ({ event, children }) => {
  const [{ isDragging }, drag] = useDrag({
    type: 'event',
    item: { type: 'event', event },
    collect: (monitor) => ({
      isDragging: monitor.isDragging(),
    }),
  });

  return (
    <div ref={drag} style={{ opacity: isDragging ? 0.5 : 1 }}>
      {children}
    </div>
  );
};

export default DraggableEvent;

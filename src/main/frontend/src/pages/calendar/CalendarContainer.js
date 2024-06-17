import React, { useState } from 'react';
import { Calendar, dateFnsLocalizer } from 'react-big-calendar';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import startOfWeek from 'date-fns/startOfWeek';
import getDay from 'date-fns/getDay';
import { ko } from 'date-fns/locale';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import withDragAndDrop from 'react-big-calendar/lib/addons/dragAndDrop';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import ScheduleModal from './ScheduleModal';
import DraggableEvent from './DraggableEvent'; // Import the DraggableEvent component

const locales = {
  'ko-KR': ko,
};

const localizer = dateFnsLocalizer({
  format: (date, formatStr, options) => format(date, formatStr, { locale: options.locale }),
  parse: (dateStr, formatStr, options) => parse(dateStr, formatStr, new Date(), { locale: options.locale }),
  startOfWeek: (date, options) => startOfWeek(date, { locale: options.locale }),
  getDay,
  locales,
});

const DnDCalendar = withDragAndDrop(Calendar);

const CalendarContainer = () => {
  const [events, setEvents] = useState([{ title: 'Meeting', start: new Date(), end: new Date(), allDay: false }]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleSelectSlot = ({ start }) => {
    setSelectedDate(start);
    setSelectedEvent(null);
    setIsModalOpen(true);
  };

  const handleSelectEvent = (event) => {
    setSelectedEvent(event);
    setSelectedDate(null);
    setIsModalOpen(true);
  };

  const addEvent = ({ title, date, teacherName, studentName, subject }) => {
    const eventDate = new Date(date);
    if (selectedEvent) {
      setEvents(events.map(event => (event === selectedEvent ? { ...event, title, start: eventDate, end: eventDate, teacherName, studentName, subject } : event)));
    } else {
      setEvents([...events, { start: eventDate, end: eventDate, title, teacherName, studentName, subject }]);
    }
    setIsModalOpen(false);
  };

  const deleteEvent = () => {
    setEvents(events.filter(event => event !== selectedEvent));
    setIsModalOpen(false);
  };

  const moveEvent = ({ event, start, end }) => {
    const duration = event.end.getTime() - event.start.getTime();
    const newEnd = new Date(start.getTime() + duration);
    const updatedEvent = {
      ...event,
      start,
      end: newEnd,
    };
    const nextEvents = events.map(existingEvent =>
      existingEvent === event ? updatedEvent : existingEvent
    );
    setEvents(nextEvents);
  };

  return (
    <DndProvider backend={HTML5Backend}>
      <div>
        <DnDCalendar
          localizer={localizer}
          events={events}
          selectable
          onSelectSlot={handleSelectSlot}
          onSelectEvent={handleSelectEvent}
          onEventDrop={moveEvent}
          onEventResize={moveEvent}
          style={{ height: '80vh' }}
          views={['month']}
        />
        <ScheduleModal
          isOpen={isModalOpen}
          onRequestClose={() => setIsModalOpen(false)}
          onSubmit={addEvent}
          onDelete={deleteEvent}
          selectedEvent={selectedEvent}
        />
      </div>
    </DndProvider>
  );
};

export default CalendarContainer;

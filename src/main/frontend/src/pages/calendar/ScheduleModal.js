import React, { useState, useEffect } from 'react';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField, Button } from '@mui/material';

const ScheduleModal = ({ isOpen, onRequestClose, onSubmit, onDelete, selectedEvent }) => {
  const [title, setTitle] = useState('');
  const [date, setDate] = useState('');
  const [teacherName, setTeacherName] = useState('');
  const [studentName, setStudentName] = useState('');
  const [subject, setSubject] = useState('');

  useEffect(() => {
    if (selectedEvent) {
      setTitle(selectedEvent.title);
      setDate(selectedEvent.start ? selectedEvent.start.toISOString().split('T')[0] : '');
      setTeacherName(selectedEvent.teacherName);
      setStudentName(selectedEvent.studentName);
      setSubject(selectedEvent.subject);
    } else {
      setTitle('');
      setDate('');
      setTeacherName('');
      setStudentName('');
      setSubject('');
    }
  }, [selectedEvent]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ title, date, teacherName, studentName, subject });
    setTitle('');
    setDate('');
    setTeacherName('');
    setStudentName('');
    setSubject('');
    onRequestClose();
  };

  const handleDelete = () => {
    if (selectedEvent) {
      onDelete();
    }
  };

  return (
    <Dialog open={isOpen} onClose={onRequestClose}>
      <DialogTitle>{selectedEvent ? 'Edit Schedule' : 'Register Schedule'}</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {selectedEvent ? 'Edit the details for the schedule.' : 'Please enter the details for the new schedule.'}
        </DialogContentText>
        <form onSubmit={handleSubmit}>
          <TextField
            autoFocus
            margin="dense"
            label="Title"
            type="text"
            fullWidth
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
          <TextField
            margin="dense"
            label="Date"
            type="date"
            fullWidth
            value={date}
            onChange={(e) => setDate(e.target.value)}
            required
          />
          <TextField
            margin="dense"
            label="Teacher Name"
            type="text"
            fullWidth
            value={teacherName}
            onChange={(e) => setTeacherName(e.target.value)}
            required
          />
          <TextField
            margin="dense"
            label="Student Name"
            type="text"
            fullWidth
            value={studentName}
            onChange={(e) => setStudentName(e.target.value)}
            required
          />
          <TextField
            margin="dense"
            label="Subject"
            type="text"
            fullWidth
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
            required
          />
          <DialogActions>
            {selectedEvent && (
              <Button onClick={handleDelete} color="secondary">
                Delete
              </Button>
            )}
            <Button onClick={onRequestClose} color="primary">
              Cancel
            </Button>
            <Button type="submit" color="primary">
              {selectedEvent ? 'Update' : 'Add'}
            </Button>
          </DialogActions>
        </form>
      </DialogContent>
    </Dialog>
  );
};

export default ScheduleModal;

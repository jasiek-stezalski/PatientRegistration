import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {CalendarService} from '../calendar.service';
import {DayPilotCalendarComponent} from 'daypilot-pro-angular';

@Component({
  selector: 'patientCalendar-component',
  templateUrl: './patientCalendar.component.html',
  styles: [``]
})
export class PatientCalendarComponent implements AfterViewInit {

  @ViewChild('calendar')
  calendar: DayPilotCalendarComponent;

  events: any;

  config: any = {
    viewType: 'Week',
    startDate: '2017-03-01',
    businessBeginsHour: 7,
    businessEndsHour: 20,
    dayBeginsHour: 7,
    dayEndsHour: 20,
    // onTimeRangeSelected: args => {
    //   let endDate = prompt('Data ostatniej wizyty:', '2017-03-10');
    //   let specialization = prompt('Specializacja:', 'Stomatologia');
    //   let careType = prompt('Rodzaj opieki:', 'Publiczna');
    //   this.calendar.control.clearSelection();
    //   if (!endDate || !specialization || !careType) {
    //     return;
    //   }
    //   let data: VisitModel = {
    //     start: args.start.toString(),
    //     end: args.end.toString(),
    //     endDate: endDate,
    //     dayInterval: 7,
    //     minuteInterval: 30,
    //     specialization: specialization,
    //     careType: careType,
    //     // Do zmiany na użytkownika zalogowanego
    //     user: {
    //       id: 2,
    //     },
    //     // Do zmiany na wybraną klinikę
    //     clinic: {
    //       id: 1,
    //     }
    //   };
    //   this.ds.createVisitModel(data).subscribe(result => {
    //     this.events.push(result);
    //     this.calendar.control.message('Model wizyty utworzony!');
    //   });
    // },

    // onEventMove: args => {
    //   let r: Resource = {
    //     id: args.newResource
    //   };
    //   let data: Event = {
    //     id: args.e.id(),
    //     start: args.newStart.toString(),
    //     end: args.newEnd.toString(),
    //     resource: r,
    //   };
    //   this.ds.moveVisitModel(data).subscribe(result => {
    //     this.calendar.control.message('Event moved');
    //   });
    // }
  };

  constructor(private ds: CalendarService) {
  }

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.ds.getVisitsInWeek(from, to).subscribe(result => this.events = result);
  }


}

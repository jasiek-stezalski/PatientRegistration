import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {CalendarService} from './calendar.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {CreateComponent} from './create/create.component';

@Component({
  selector: 'calendar-component',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('create') create: CreateComponent;

  events: any[] = [];

  navigatorConfig: any = {
    locale: 'pl-pl',
    showMonths: 3,
    skipMonths: 3,
    selectMode: "week",
    cellWidth: 30,
    cellHeight: 25,
    dayHeaderHeight: 23,
    titleHeight: 23
  };

  config: any = {
    startDate: DayPilot.Date.today(),
    locale: 'pl-pl',
    viewType: 'Week',
    businessBeginsHour: 7,
    businessEndsHour: 20,
    dayBeginsHour: 7,
    dayEndsHour: 20,
    onTimeRangeSelected: args => {
      this.create.show(args);
    }
    // onTimeRangeSelected: args => {
    //   let endDate = prompt('Data ostatniej wizyty:', Date.now().toString());
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
    //       idUser: 2,
    //     },
    //     // Do zmiany na wybraną klinikę
    //     clinic: {
    //       idClinic: 1,
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
    //   this.ds.moveEvent(data).subscribe(result => {
    //     this.calendar.control.message('Event moved');
    //   });
    // }
  };

  constructor(private ds: CalendarService) {
  }

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.ds.getVisitModelsInWeek(from, to).subscribe(result => this.events = result);
  }

  createClosed(args) {
    if (args.result) {
      this.events.push(args.result);
      this.calendar.control.message('Utworzono.');
    }
    this.calendar.control.clearSelection();
  }

  navigatePrevious(event): void {
    event.preventDefault();
    this.config.startDate = this.config.startDate.addDays(-7);
  }

  navigateNext(event): void {
    event.preventDefault();
    this.config.startDate = this.config.startDate.addDays(7);
  }

  navigateToday(event): void {
    event.preventDefault();
    this.config.startDate = DayPilot.Date.today();
  }

}

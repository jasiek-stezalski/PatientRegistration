import {AfterViewInit, Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {CalendarService} from './calendar.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {CreateComponent} from './create/create.component';
import {VisitModel} from '../models/visitModel.model';
import {User} from '../models/user.model';
import {Router} from '@angular/router';

@Component({
  selector: 'calendar-component',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('create') create: CreateComponent;

  events: any[] = [];
  currentUser: User;

  constructor(public router: Router, private service: CalendarService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }


  navigatorConfig: any = {
    locale: 'pl-pl',
    showMonths: 3,
    skipMonths: 3,
    selectMode: 'week',
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
    cellDuration: 15,

    onTimeRangeSelected: args => {
      if (args.start < CalendarService.minimalDate())
        this.calendar.control.message('Model wizyty nie może być utworzony ze wsteczną datą!');
      else
        this.create.show(args);
    },

    eventMoveHandling: 'Update',
    onEventMove: args => {
      let data: VisitModel = {
        id: args.e.id(),
        start: args.newStart.toString(),
        end: args.newEnd.toString()
      };
      this.service.moveVisitModel(data).subscribe(() => {
        this.calendar.control.message('Model wizyty został zmieniony');
      });
    },

    eventDeleteHandling: 'Update',
    onEventDelete: args => {
      this.service.deleteVisitModel(args.e.id()).subscribe(() => {
        this.calendar.control.message('Model wizyty został usunięty');
      });
    },
  };


  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.service.getVisitModelsInWeek(from, to).subscribe(result => this.events = result);
  }

  createClosed(args) {
    if (args.result) {
      this.events.push(args.result);
      this.calendar.control.message('Model wizyty został utworzony.');
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

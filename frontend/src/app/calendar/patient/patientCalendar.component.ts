import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {CalendarService} from '../calendar.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {CreateComponent} from '../create/create.component';

@Component({
  selector: 'patientCalendar-component',
  templateUrl: './patientCalendar.component.html',
  styleUrls: ['./patientCalendar.component.css']
})
export class PatientCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('create') create: CreateComponent;

  events: any[] = [];

  constructor(private service: CalendarService) {
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

    timeRangeSelectedHandling: "Disabled",
    eventDeleteHandling: "Disabled",
    eventMoveHandling: "Disabled",
    eventResizeHandling: "Disabled",
    eventHoverHandling: "Disabled",

  };

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.service.getVisitsInWeek(from, to).subscribe(result => this.events = result);
  }

  createClosed(args) {
    if (args.result) {
      this.events.push(args.result);
      this.calendar.control.message('Zostałeś zapisany na wizytę.');
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

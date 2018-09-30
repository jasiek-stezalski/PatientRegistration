import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {CalendarService} from '../calendar.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {CreateComponent} from '../create/create.component';
import {Visit} from '../../models/visit.model';
import {User} from '../../models/user.model';

@Component({
  selector: 'patientCalendar-component',
  templateUrl: './patientCalendar.component.html',
  styleUrls: ['./patientCalendar.component.css']
})
export class PatientCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('create') create: CreateComponent;

  events: Visit[] = [];

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

    timeRangeSelectedHandling: 'Disabled',
    eventDeleteHandling: 'Disabled',
    eventMoveHandling: 'Disabled',
    eventResizeHandling: 'Disabled',
    eventHoverHandling: 'Disabled',
    eventClickHandling: 'Enabled',

    onEventClicked: args => {
      let visit: Visit = this.events.find(a => a.id == args.e.id());
      if (('' + visit.start + '').substring(0, 10) <= ('' + DayPilot.Date.today() + '').substring(0, 10)) {
        this.calendar.control.message('Ten termin jest już nieaktualny!');
      }
      else if (visit.user != null)
        this.calendar.control.message('Ten termin jest już zarezerwowany!');
      else {
        let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
        this.service.bookVisit(args.e.id(), user.id).subscribe(() => {
          this.calendar.control.message('Zostałeś zapisany na wizytę!');
          this.ngAfterViewInit();
        });
      }

    },


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

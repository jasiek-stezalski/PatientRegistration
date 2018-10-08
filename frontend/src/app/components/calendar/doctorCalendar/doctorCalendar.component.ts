import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {User} from "../../../models/user.model";

@Component({
  selector: 'doctorCalendar-component',
  templateUrl: './doctorCalendar.component.html',
  styleUrls: ['./doctorCalendar.component.css']
})
export class DoctorCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;

  events: Visit[] = [];

  constructor(private visitService: VisitService) {
  }

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.getVisitsInWeekByDoctor(from, to, user.id).subscribe(result => {
      this.events = result;
    });

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
    eventMoveHandling: 'Disabled',
    eventResizeHandling: 'Disabled',
    eventClickHandling: 'Disabled',
    eventHoverHandling: 'Disabled',

    eventDeleteHandling: 'Update',
    onEventDelete: args => {
      let visit: Visit = this.events.find(a => a.id == args.e.id());
      if (('' + visit.start + '').substring(0, 10) <= ('' + DayPilot.Date.today() + '').substring(0, 10)) {
        this.calendar.control.message('Nie można odwołać wizyty która się już odbyła!');
        this.ngAfterViewInit();
        return;
      }
      if (confirm('Czy na pewno chcesz odwołać tą wizytę?')) {
        this.visitService.deleteVisit(args.e.id()).subscribe(() => {
          this.calendar.control.message('Wizyta została odwołana');
        });
      } else this.ngAfterViewInit();
    },

  };

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

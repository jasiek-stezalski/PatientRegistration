import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {BookComponent} from '../book/book.component';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from "../../../models/user.model";
import {isUndefined} from "util";

@Component({
  selector: 'patientCalendar-component',
  templateUrl: './patientCalendar.component.html',
  styleUrls: ['./patientCalendar.component.css']
})
export class PatientCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('book') book: BookComponent;

  events: Visit[] = [];

  constructor(private visitService: VisitService, private router: Router, private route: ActivatedRoute) {
  }

  ngAfterViewInit(): void {
    this.route.queryParams
      .subscribe(params => {
        if (isUndefined(params.careType) || isUndefined(params.city) || isUndefined(params.specialization))
          this.router.navigate(['/']);

        this.visitService.getVisitsByVisitFilter(params.careType, params.city, params.specialization).subscribe(result => {
          this.events = result;
        })
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
    eventDeleteHandling: 'Disabled',
    eventMoveHandling: 'Disabled',
    eventResizeHandling: 'Disabled',
    eventClickHandling: 'Enabled',

    onEventClicked: args => {
      let visit: Visit = this.events.find(a => a.id == args.e.id());
      this.book.show(visit);
      visit.user = new User();
      this.calendar.control.clearSelection();

    }

  };

  createClosed(args) {
    if (args.result) {
      alert('Zostałeś zapisany na wizytę!');
      this.router.navigate(['/userVisits']);
    }
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

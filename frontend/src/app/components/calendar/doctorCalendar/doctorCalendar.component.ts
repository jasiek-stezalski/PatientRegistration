import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {User} from "../../../models/user.model";
import {VisitModel} from "../../../models/visitModel.model";
import {InfoComponent} from "./info/info.component";
import {BookByDoctorComponent} from "./bookByDoctor/bookByDoctor.component";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'doctorCalendar-component',
  templateUrl: './doctorCalendar.component.html',
  styleUrls: ['./doctorCalendar.component.css']
})
export class DoctorCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('info') info: InfoComponent;
  @ViewChild('bookByDoctor') book: BookByDoctorComponent;

  events: Visit[] = [];

  private sub: any;

  constructor(private visitService: VisitService, private route: ActivatedRoute, private userService: UserService) {
  }

  ngAfterViewInit(): void {
    this.sub = this.route.params.subscribe(params => {
      let idUser = +params['id'];
      if (!isNaN(idUser)) {
        this.userService.getUserById(idUser).subscribe(result => {
          this.info.user = result;
        })
      }

    });

    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.getVisitsInWeekByDoctor(from, to, user.id).subscribe(result => {
      this.events = result;
      this.events.forEach(v => {
        if (v.text === 'Zajęte')
          v.barColor = '#487bcc';
        else if (v.text === 'Zakończone')
          v.barColor = '#c6ccc6';
        else v.barColor = 'grey';
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
    eventResizeHandling: 'Disabled',
    eventHoverHandling: 'Disabled',

    onEventClicked: args => {
      let visit: Visit = this.events.find(a => a.id == args.e.id());
      if (visit.user != null)
        this.info.show(visit);
      else if (('' + visit.start + '').substring(0, 10) <= ('' + DayPilot.Date.today() + '').substring(0, 10)) {
        this.calendar.control.message('Ten termin jest już nieaktualny!');
      }
      else if (this.info.user.id != null) {
        this.book.show(visit, this.info.user);
      }
    },

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

    eventMoveHandling: 'Update',
    onEventMove: args => {
      let visit: Visit = this.events.find(a => a.id == args.e.id());
      if (('' + visit.start + '').substring(0, 10) <= ('' + DayPilot.Date.today() + '').substring(0, 10)) {
        this.calendar.control.message('Nie można zmienić terminu wizyty która się już odbyła!');
        this.ngAfterViewInit();
        return;
      }
      if (confirm('Czy na pewno chcesz odwołać tą wizytę?')) {
        let data: VisitModel = {
          id: args.e.id(),
          start: args.newStart.toString(),
          end: args.newEnd.toString()
        };
        this.visitService.moveVisit(data).subscribe(() => {
          this.calendar.control.message('Termin wizyty został zmieniony');
        });
      } else this.ngAfterViewInit();
    },

  };

  bookClosed(args) {
    if (args.result) {
      this.info.user.id = null;
      this.calendar.control.message('Model wizyty został utworzony.');
    }
    this.calendar.control.clearSelection();
  }

  infoClosed(args) {
    if (this.info.user.id != null) {
      this.calendar.control.message('Wybierz termin następnej wizyty');
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

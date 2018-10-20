import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {VisitModelService} from '../../../services/visitModel.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {UserService} from '../../../services/user.service';
import {ClinicService} from '../../../services/clinic.service';
import {BookComponent} from './book/book.component';
import {Router} from '@angular/router';
import {User} from "../../../models/user.model";
import {Clinic} from "../../../models/clinic.model";

@Component({
  selector: 'patientCalendar-component',
  templateUrl: './patientCalendar.component.html',
  styleUrls: ['./patientCalendar.component.css']
})
export class PatientCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('book') book: BookComponent;

  events: Visit[] = [];
  events2: Visit[] = [];
  clinicsBase: Clinic[] = [];

  constructor(private visitModelService: VisitModelService, private visitService: VisitService,
              private userService: UserService, private clinicService: ClinicService, private router: Router) {
  }

  ngAfterViewInit(): void {
    // let from = this.calendar.control.visibleStart();
    // let to = this.calendar.control.visibleEnd();
    // this.visitService.getVisitsInWeek(from, to).subscribe(result => {
    //   this.events = result;
    //   this.events2 = result;
    //   this.events.forEach(v => {
    //     if (v.text === 'Zajęte')
    //       v.barColor = 'grey';
    //     else if (v.text === 'Zakończone')
    //       v.barColor = '#c6ccc6';
    //   })
    //   this.events2.forEach(v => {
    //     if (v.text === 'Zajęte')
    //       v.barColor = 'grey';
    //     else if (v.text === 'Zakończone')
    //       v.barColor = '#c6ccc6';
    //   })
    // });
    // this.cities.add('-');
    // this.clinics.push('-');
    // this.clinicService.getClinics()
    //   .subscribe(data => {
    //     this.clinicsBase = data;
    //     data.forEach(i => {
    //       this.cities.add(i.city);
    //       this.clinics.push(i.name + ', ' + i.address);
    //     });
    //   });
    //
    // this.doctors.push('-');
    // this.userService.getUsersByRole('DOCTOR')
    //   .subscribe(data => {
    //     data.forEach(i => {
    //       this.doctors.push(i.firstName + ' ' + i.lastName);
    //     });
    //   });
    // this.specialization.push('-');
    // this.specialization.sort();
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
      if (('' + visit.start + '').substring(0, 10) <= ('' + DayPilot.Date.today() + '').substring(0, 10)) {
        this.calendar.control.message('Ten termin jest już nieaktualny!');
      }
      else if (visit.user != null)
        this.calendar.control.message('Ten termin jest już zarezerwowany!');
      else {
        if (sessionStorage.getItem('currentUser') != null) {
          this.book.show(visit);
          visit.user = new User();
          this.calendar.control.clearSelection();
        }
        else
          this.router.navigate(['/login']);
      }
    },

  };

  createClosed(args) {
    if (args.result) {
      this.calendar.control.message('Zostałeś zapisany na wizytę!');
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

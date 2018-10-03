import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {VisitModelService} from '../../../services/visitModel.service';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {CreateComponent} from '../create/create.component';
import {Visit} from '../../../models/visit.model';
import {User} from '../../../models/user.model';
import {VisitService} from '../../../services/visit.services';
import {UserService} from '../../../services/user.service';
import {ClinicService} from '../../../services/clinic.service';

@Component({
  selector: 'patientCalendar-component',
  templateUrl: './patientCalendar.component.html',
  styleUrls: ['./patientCalendar.component.css']
})
export class PatientCalendarComponent implements AfterViewInit {

  @ViewChild('calendar') calendar: DayPilotCalendarComponent;
  @ViewChild('create') create: CreateComponent;

  events: Visit[] = [];
  events2: Visit[] = [];

  itemMap: Map<String, Item> = new Map<String, Item>();

  careType: any[] = [
    '-',
    'Publiczna',
    'Prywatna',
  ];

  clinics: Array<String> = [];
  doctors: Array<String> = [];
  specialization = JSON.parse(JSON.stringify(this.visitModelService.specialization));

  filter: any = {
    careType: '',
    clinic: '',
    doctor: '',
    specialization: '',
  };

  constructor(private visitModelService: VisitModelService, private visitService: VisitService,
              private userService: UserService, private clinicService: ClinicService) {
    let item: Item = {
      isFilter: false,
      name: '',
    };
    this.itemMap.set('careType', item);
    let item2: Item = {
      isFilter: false,
      name: '',
    };
    this.itemMap.set('clinic', item2);
    let item3: Item = {
      isFilter: false,
      name: '',
    };
    this.itemMap.set('doctor', item3);
    let item4: Item = {
      isFilter: false,
      name: '',
    };
    this.itemMap.set('specialization', item4);
  }

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.visitService.getVisitsInWeek(from, to).subscribe(result => {
      this.events = result;
      this.events2 = result;
    });
    this.clinics.push('-');
    this.clinicService.getClinics()
      .subscribe(data => {
        data.forEach(i => {
          this.clinics.push(i.name + ', ' + i.address);
        });
      });
    this.doctors.push('-');
    this.userService.getUsersByRole('DOCTOR')
      .subscribe(data => {
        data.forEach(i => {
          this.doctors.push(i.firstName + ' ' + i.lastName);
        });
      });
    this.specialization.push('-');
    this.specialization.sort();
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
        this.visitService.bookVisit(args.e.id(), user.id).subscribe(() => {
          this.calendar.control.message('Zostałeś zapisany na wizytę!');
          visit.text = 'Zajęte';
        });
      }

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


  changeCareType(val) {
    this.itemMap.get('careType').name = val;
    this.itemMap.get('careType').isFilter = val != '-';
    this.doFilter();
  }

  changeClinic(val) {
    this.itemMap.get('clinic').name = val;
    this.itemMap.get('clinic').isFilter = val != '-';
    this.doFilter();
  }

  changeDoctor(val) {
    this.itemMap.get('doctor').name = val;
    this.itemMap.get('doctor').isFilter = val != '-';
    this.doFilter();
  }

  changeSpecialization(val) {
    this.itemMap.get('specialization').name = val;
    this.itemMap.get('specialization').isFilter = val != '-';
    this.doFilter();
  }

  doFilter() {
    this.events = this.events2;

    if (this.itemMap.get('careType').isFilter)
      this.events = this.events.filter(value => value.visitModel.careType == this.itemMap.get('careType').name);
    if (this.itemMap.get('clinic').isFilter)
      this.events = this.events.filter(value =>
        (value.visitModel.clinic.name + ', ' + value.visitModel.clinic.address) == this.itemMap.get('clinic').name);
    if (this.itemMap.get('doctor').isFilter)
      this.events = this.events.filter(value =>
        (value.visitModel.user.firstName + ' ' + value.visitModel.user.lastName) == this.itemMap.get('doctor').name);
    if (this.itemMap.get('specialization').isFilter)
      this.events = this.events.filter(value => value.visitModel.specialization == this.itemMap.get('specialization').name);
  }


  clearFilter() {
    this.events = this.events2;
    this.itemMap.forEach(i => i.isFilter = false);
    return false;
  }

}

export class Item {
  isFilter: Boolean;
  name: String;
}

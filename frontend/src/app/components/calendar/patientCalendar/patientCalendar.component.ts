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
import {Item} from "../../../resources/item.model";

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

  itemMap: Map<String, Item> = new Map<String, Item>();

  careType: String[] = ['-', 'Publiczna', 'Prywatna',];
  cities: Set<String> = new Set<String>();
  clinics: Array<String> = [];
  doctors: Array<String> = [];
  specialization = JSON.parse(JSON.stringify(this.visitModelService.specialization));

  filter: any = {
    careType: '',
    cities: '',
    clinic: '',
    doctor: '',
    specialization: '',
    minPrice: '',
    maxPrice: ''
  };

  constructor(private visitModelService: VisitModelService, private visitService: VisitService,
              private userService: UserService, private clinicService: ClinicService, private router: Router) {
    this.itemMap.set('careType', {isFilter: false, name: '',});
    this.itemMap.set('city', {isFilter: false, name: '',});
    this.itemMap.set('clinic', {isFilter: false, name: '',});
    this.itemMap.set('doctor', {isFilter: false, name: '',});
    this.itemMap.set('specialization', {isFilter: false, name: '',});
    this.itemMap.set('minPrice', {isFilter: false, name: '',});
    this.itemMap.set('maxPrice', {isFilter: false, name: '',});
  }

  ngAfterViewInit(): void {
    let from = this.calendar.control.visibleStart();
    let to = this.calendar.control.visibleEnd();
    this.visitService.getVisitsInWeek(from, to).subscribe(result => {
      this.events = result;
      this.events2 = result;
    });
    this.cities.add('-');
    this.clinics.push('-');
    this.clinicService.getClinics()
      .subscribe(data => {
        this.clinicsBase = data;
        data.forEach(i => {
          this.cities.add(i.city);
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


  changeCareType(val) {
    this.itemMap.get('careType').name = val;
    this.itemMap.get('careType').isFilter = val != '-';
    this.doFilter();
  }

  changeCity(val) {
    this.itemMap.get('city').name = val;
    this.itemMap.get('city').isFilter = val != '-';

    this.clinics = [];
    this.clinics.push('-');
    this.itemMap.get('clinic').isFilter = false;
    if (val === '-') {
      this.filter.clinic = '-';
      this.clinicsBase
        .forEach(i => this.clinics.push(i.name + ', ' + i.address));
    }
    else {
      this.clinicsBase
        .filter(v => v.city === val)
        .forEach(i => this.clinics.push(i.name + ', ' + i.address));
    }

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

  changeMinPrice(val) {
    this.itemMap.get('minPrice').name = val;
    this.itemMap.get('minPrice').isFilter = val != '-';
    this.doFilter();
  }

  changeMaxPrice(val) {
    this.itemMap.get('maxPrice').name = val;
    this.itemMap.get('maxPrice').isFilter = val != '-';
    this.doFilter();
  }

  doFilter() {
    this.events = this.events2;

    if (this.itemMap.get('careType').isFilter)
      this.events = this.events.filter(value => value.visitModel.careType === this.itemMap.get('careType').name);
    if (this.itemMap.get('city').isFilter)
      this.events = this.events.filter(value => value.visitModel.clinic.city === this.itemMap.get('city').name);
    if (this.itemMap.get('clinic').isFilter)
      this.events = this.events.filter(value =>
        (value.visitModel.clinic.name + ', ' + value.visitModel.clinic.address) === this.itemMap.get('clinic').name);
    if (this.itemMap.get('doctor').isFilter)
      this.events = this.events.filter(value =>
        (value.visitModel.user.firstName + ' ' + value.visitModel.user.lastName) === this.itemMap.get('doctor').name);
    if (this.itemMap.get('specialization').isFilter)
      this.events = this.events.filter(value => value.visitModel.specialization === this.itemMap.get('specialization').name);
    if (this.itemMap.get('minPrice').isFilter)
      this.events = this.events.filter(value => value.visitModel.price >= +this.itemMap.get('minPrice').name);
    if (this.itemMap.get('maxPrice').isFilter)
      this.events = this.events.filter(value => value.visitModel.price <= +this.itemMap.get('maxPrice').name);
  }

  clearFilter() {
    this.events = this.events2;
    this.itemMap.forEach(i => i.isFilter = false);
    return false;
  }
}

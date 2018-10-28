import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {DayPilot, DayPilotCalendarComponent} from 'daypilot-pro-angular';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {BookComponent} from '../book/book.component';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../../models/user.model';
import {isUndefined} from 'util';
import {Item} from '../../../resources/item.model';
import {ClinicService} from '../../../services/clinic.service';
import {UserService} from '../../../services/user.service';

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

  doctors: Set<string> = new Set<string>();

  itemMap: Map<string, Item> = new Map<string, Item>();


  filter: any = {
    doctor: '',
    minPrice: '',
    maxPrice: ''
  };

  idOldVisit: number;
  private sub: any;


  constructor(private visitService: VisitService, private clinicService: ClinicService, private userService: UserService,
              private router: Router, private route: ActivatedRoute) {
    this.itemMap.set('doctor', {isFilter: false, name: '',});
    this.itemMap.set('minPrice', {isFilter: false, name: '',});
    this.itemMap.set('maxPrice', {isFilter: false, name: '',});
  }

  ngAfterViewInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.idOldVisit = +params['id'];
    });

    this.route.queryParams
      .subscribe(params => {
        if (isUndefined(params.careType) || isUndefined(params.city) || isUndefined(params.specialization))
          this.router.navigate(['/']);

        this.visitService.getVisitsByVisitFilter(params.careType, params.city, params.idClinic, params.specialization).subscribe(result => {
          this.events = result;
          this.events2 = result;

          this.events.forEach(v => {
            if (v.text === 'Zajęte')
              v.barColor = 'grey';
            else if (v.text === 'Zakończone')
              v.barColor = '#c6ccc6';
            else v.barColor = '#487bcc';
          });

          this.doctors.add('-');

          result.forEach(v => this.doctors.add(v.visitModel.user.firstName + ' ' + v.visitModel.user.lastName));

        });

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
      this.book.show(visit, this.idOldVisit);
      visit.user = new User();
      this.calendar.control.clearSelection();
    }

  };

  createClosed(args) {
    if (args.result) {
      if (isNaN(this.idOldVisit))
        alert('Zostałeś zapisany na wizytę!');
      else
        alert('Termin twojej wizyty został zmieniony!');

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

  changeDoctor(val) {
    this.itemMap.get('doctor').name = val;
    this.itemMap.get('doctor').isFilter = val != '-';
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

    if (this.itemMap.get('doctor').isFilter)
      this.events = this.events.filter(value =>
        (value.visitModel.user.firstName + ' ' + value.visitModel.user.lastName) === this.itemMap.get('doctor').name);
    if (this.itemMap.get('minPrice').isFilter)
      this.events = this.events.filter(value => value.visitModel.price >= +this.itemMap.get('minPrice').name);
    if (this.itemMap.get('maxPrice').isFilter)
      this.events = this.events.filter(value => value.visitModel.price <= +this.itemMap.get('maxPrice').name);
  }

  clearFilter() {
    this.events = this.events2;
    this.itemMap.forEach(i => i.isFilter = false);
  }

}

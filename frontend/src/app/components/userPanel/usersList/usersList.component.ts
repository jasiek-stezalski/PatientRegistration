import {AfterViewInit, Component} from '@angular/core';
import {User} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';
import {Item} from '../../calendar/patientCalendar/patientCalendar.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-usersList',
  templateUrl: './usersList.component.html',
  styleUrls: ['./usersList.component.css']
})
export class UsersListComponent implements AfterViewInit {

  patients: User[] = [];
  patientsBase: User[] = [];

  itemMap: Map<String, Item> = new Map<String, Item>();

  filter: any = {
    firstName: '',
    lastName: '',
  };

  constructor(private userService: UserService, private router: Router) {
    this.itemMap.set('firstName', {isFilter: false, name: '',});
    this.itemMap.set('lastName', {isFilter: false, name: '',});
  }

  ngAfterViewInit(): void {
    let doctor: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.userService.getUsersByIdDoctor(doctor.id)
      .subscribe(data => {
        this.patientsBase = data;
        this.patients = data;
      });
  };

  changeFirstName(val) {
    this.itemMap.get('firstName').name = val;
    this.itemMap.get('firstName').isFilter = val != '-';
    this.doFilter();
  }

  changeLastName(val) {
    this.itemMap.get('lastName').name = val;
    this.itemMap.get('lastName').isFilter = val != '-';
    this.doFilter();
  }

  doFilter() {
    this.patients = this.patientsBase;

    if (this.itemMap.get('firstName').isFilter) {
      this.patients = this.patients
        .filter(value => value.firstName.toLowerCase().startsWith(this.itemMap.get('firstName').name.toLowerCase().toString()));
    }

    if (this.itemMap.get('lastName').isFilter)
      this.patients = this.patients
        .filter(value => value.lastName.toLowerCase().startsWith(this.itemMap.get('lastName').name.toLowerCase().toString()));
  }

  clearFilter() {
    this.patients = this.patientsBase;
    this.itemMap.forEach(i => i.isFilter = false);
    return false;
  }

  openUserHistory(id: string | number) {
    this.router.navigate(['userHistory/', id]);
  }
}

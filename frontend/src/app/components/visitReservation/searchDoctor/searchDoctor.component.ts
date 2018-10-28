import {AfterViewInit, Component} from '@angular/core';
import {User} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-searchDoctor',
  templateUrl: './searchDoctor.component.html',
  styleUrls: ['./searchDoctor.component.css']
})
export class SearchDoctorComponent implements AfterViewInit {

  doctors: User[] = [];
  idDoctor: number;

  constructor(private userService: UserService, private router: Router) {
  }

  ngAfterViewInit(): void {
    this.userService.getUsersByRole('DOCTOR')
      .subscribe(data => {
        this.doctors = data;
      });

  }

  submit() {
    this.router.navigate(['/patientCalendar'], {
      queryParams: {
        idDoctor: this.idDoctor,
      }
    });
  }


}

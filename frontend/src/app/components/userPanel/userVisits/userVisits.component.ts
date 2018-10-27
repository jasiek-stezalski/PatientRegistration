import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {User} from '../../../models/user.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-userVisits',
  templateUrl: './userVisits.component.html',
  styleUrls: ['./userVisits.component.css']
})
export class UserVisitsComponent implements OnInit {

  visits: Visit[] = [];

  constructor(private userService: UserService, private visitService: VisitService, private router: Router) {
  }

  ngOnInit() {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.getActualVisitsByIdUser(user.id)
      .subscribe(data => this.visits = data);
  }

  cancelVisit(id: string | number) {
    if (confirm('Czy na pewno chcesz odwołać tą wizytę?')) {
      let visit = this.visits.find(v => v.id === id);
      this.visitService.cancelVisit(visit).subscribe(() => {
        alert('Wizyta została odwołana');
        this.ngOnInit();
      });
    }
  }

  changeVisit(id: string | number) {
    let visit = this.visits.find(v => v.id === id);
    if (confirm('Czy na pewno chcesz zmienić termin wizyty?')) {
      this.router.navigate(['/patientCalendar', id], {
        queryParams: {
          careType: visit.visitModel.careType,
          city: visit.visitModel.clinic.city,
          idClinic: visit.visitModel.clinic.id,
          specialization: visit.visitModel.user.specialization
        }
      });
    }
  }
}

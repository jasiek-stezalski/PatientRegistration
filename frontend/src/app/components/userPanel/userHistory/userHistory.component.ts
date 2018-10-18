import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../../models/user.model';

@Component({
  selector: 'app-userHistory',
  templateUrl: './userHistory.component.html',
  styleUrls: ['./userHistory.component.css']
})
export class UserHistoryComponent implements OnInit {

  visitsBase: Visit[] = [];
  visits: Visit[] = [];

  specialization: Set<String> = new Set<String>();

  filter: any = {
    specialization: '',
  };

  id: number;
  private sub: any;

  constructor(private userService: UserService, private visitService: VisitService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id'];
    });

    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.specialization.add('-');
    if (user.role === 'DOCTOR') {
      this.visitService.getHistoricalVisitsByIdUser(this.id)
        .subscribe(data => {
          this.visitsBase = data;
          this.visits = data;
          this.visitsBase.forEach(value => {
            this.specialization.add(value.visitModel.specialization);
          });
        });
    } else {
      this.visitService.getHistoricalVisitsByIdUser(user.id)
        .subscribe(data => {
          this.visitsBase = data;
          this.visits = data;
          this.visitsBase.forEach(value => {
            this.specialization.add(value.visitModel.specialization);
          });
        });
    }

  }

  setVisit(idUser: string | number) {
    this.visitService.getHistoricalVisitsByIdUser(idUser)
      .subscribe(data => {
        this.visitsBase = data;
        this.visits = data;
        this.visitsBase.forEach(value => {
          this.specialization.add(value.visitModel.specialization);
        });
      });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  changeSpecialization(val) {
    this.visits = this.visitsBase;
    this.visits = this.visits.filter(value => value.visitModel.specialization === val);
  }

  clearFilter() {
    this.visits = this.visitsBase;
    return false;
  }

}

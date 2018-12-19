import {Component, OnInit, ViewChild} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../../../models/user.model';
import {RatingComponent} from './Rating/rating.component';

@Component({
  selector: 'app-userHistory',
  templateUrl: './userHistory.component.html',
  styleUrls: ['./userHistory.component.css']
})
export class UserHistoryComponent implements OnInit {

  @ViewChild('rating') rating: RatingComponent;

  visitsBase: Visit[] = [];
  visits: Visit[] = [];

  specialization: Set<string> = new Set<string>();

  filter: any = {
    specialization: '',
  };

  id: number;
  private sub: any;

  constructor(private userService: UserService, private visitService: VisitService,
              private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id'];
    });

    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.specialization.add('-');
    if (user.role === 'DOCTOR' && !isNaN(this.id)) {
      this.visitService.getHistoricalVisitsByIdUser(this.id)
        .subscribe(data => {
          this.visitsBase = data;
          this.visits = data;
          this.visitsBase.forEach(value => {
            this.specialization.add(value.visitModel.user.specialization);
          });
        });
    } else {
      this.visitService.getHistoricalVisitsByIdUser(user.id)
        .subscribe(data => {
          this.visitsBase = data;
          this.visits = data;
          this.visitsBase.forEach(value => {
            this.specialization.add(value.visitModel.user.specialization);
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
          this.specialization.add(value.visitModel.user.specialization);
        });
      });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  changeSpecialization(val) {
    this.visits = this.visitsBase;
    this.visits = this.visits.filter(value => value.visitModel.user.specialization === val);
  }

  clearFilter() {
    this.visits = this.visitsBase;
    return false;
  }

  doctorCalendar(idDoctor: string | number) {
    this.router.navigate(['/patientCalendar'], {
      queryParams: {
        idDoctor: idDoctor,
      }
    });
  }

  rateVisit(visit: Visit) {
    this.rating.show(visit);
  }

  createClosed(args) {
    if (args.result) {
      alert('Oceniłeś wizytę!');
      this.router.navigate(['/userHistory']);
    }
  }

}

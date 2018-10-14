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

  visits: Visit[] = [];

  id: number;
  private sub: any;

  constructor(private userService: UserService, private visitService: VisitService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id'];
    });

    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    if (user.role === 'DOCTOR') {
      this.visitService.getVisitsByIdUser(this.id)
        .subscribe(data => {
          this.visits = data;
        });
    } else {
      this.visitService.getVisitsByIdUser(user.id)
        .subscribe(data => {
          this.visits = data;
        });
    }


  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}

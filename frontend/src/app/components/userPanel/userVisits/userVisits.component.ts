import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {User} from '../../../models/user.model';

@Component({
  selector: 'app-userVisits',
  templateUrl: './userVisits.component.html',
  styleUrls: ['./userVisits.component.css']
})
export class UserVisitsComponent implements OnInit {

  visits: Visit[] = [];

  constructor(private userService: UserService, private visitService: VisitService) {
  }

  ngOnInit() {
    let user: User = JSON.parse(sessionStorage.getItem('currentUser'));
    this.visitService.getActualVisitsByIdUser(user.id)
      .subscribe(data => this.visits = data);
  }

}

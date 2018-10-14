import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {Visit} from '../../../models/visit.model';
import {VisitService} from '../../../services/visit.services';
import {ActivatedRoute} from '@angular/router';

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

    this.visitService.getVisitsByIdUser(this.id)
      .subscribe(data => {
        this.visits = data;
      });

  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}

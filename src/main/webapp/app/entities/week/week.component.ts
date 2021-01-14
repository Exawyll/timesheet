import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWeek } from 'app/shared/model/week.model';
import { WeekService } from './week.service';
import { WeekDeleteDialogComponent } from './week-delete-dialog.component';

@Component({
  selector: 'jhi-week',
  templateUrl: './week.component.html',
})
export class WeekComponent implements OnInit, OnDestroy {
  weeks?: IWeek[];
  eventSubscriber?: Subscription;

  constructor(protected weekService: WeekService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.weekService.query().subscribe((res: HttpResponse<IWeek[]>) => (this.weeks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWeeks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWeek): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWeeks(): void {
    this.eventSubscriber = this.eventManager.subscribe('weekListModification', () => this.loadAll());
  }

  delete(week: IWeek): void {
    const modalRef = this.modalService.open(WeekDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.week = week;
  }
}

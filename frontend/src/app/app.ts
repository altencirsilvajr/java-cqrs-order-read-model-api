import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface Overview { acceptedOrders: number; pendingOutbox: number; publishedEvents: number; projectedOrders: number; }
interface AcceptedOrder { orderId: string; eventId: string; projectionState: string; acceptedAt: string; statusUrl: string; }
interface ProjectionStatus { orderId: string; state: 'PENDING' | 'PROJECTED' | 'STALE'; acceptedAt: string; projectedAt: string | null; lagMilliseconds: number; }

@Component({ selector: 'app-root', imports: [FormsModule, DatePipe], templateUrl: './app.html', styleUrl: './app.scss' })
export class App implements OnDestroy {
  private readonly http = inject(HttpClient);
  private poll?: ReturnType<typeof setInterval>;
  readonly overview = signal<Overview | null>(null); readonly accepted = signal<AcceptedOrder | null>(null); readonly status = signal<ProjectionStatus | null>(null); readonly busy = signal(false); readonly error = signal('');
  customerId = 'customer-42'; sku = 'JAVA-21'; quantity = 2; unitPrice = 19.95;
  constructor() { this.refreshOverview(); }
  placeOrder(): void {
    this.busy.set(true); this.error.set(''); this.status.set(null);
    this.http.post<AcceptedOrder>('/api/orders',{customerId:this.customerId,items:[{sku:this.sku,quantity:this.quantity,unitPrice:this.unitPrice}]}).subscribe({next:order=>{this.accepted.set(order);this.busy.set(false);this.startPolling(order.statusUrl);this.refreshOverview();},error:()=>{this.error.set('The API did not accept the order. Check the backend and input.');this.busy.set(false);}});
  }
  private startPolling(url:string):void { if(this.poll)clearInterval(this.poll);this.loadStatus(url);this.poll=setInterval(()=>this.loadStatus(url),500); }
  private loadStatus(url:string):void { this.http.get<ProjectionStatus>(url).subscribe({next:value=>{this.status.set(value);this.refreshOverview();if(value.state==='PROJECTED'&&this.poll){clearInterval(this.poll);this.poll=undefined;}},error:()=>undefined}); }
  private refreshOverview():void { this.http.get<Overview>('/api/observability/overview').subscribe({next:value=>this.overview.set(value),error:()=>undefined}); }
  ngOnDestroy():void { if(this.poll)clearInterval(this.poll); }
}
